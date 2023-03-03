import axios from 'axios';
// import { useAuthStore } from '../stores/auth';
import router from '../router/index';

// const authStore = useAuthStore();

// export async function login(username, password) {
//   axios
//     .post('/api/auth/login', {
//       username: username,
//       password: password,
//     })
//     .then((response) => {
//       if (response.status === 200) {
//         console.log(response.data);
//         console.log('accessToken: ' + response.data.accessToken);
//         authStore.accessToken = response.data.accessToken;
//         authStore.refreshToken = response.data.refreshToken;
//         authStore.role = response.data.role;
//         return true;
//       }
//     })
//     .catch((e) => {
//       console.error('error : ', e);
//     });
//   return false;
// }

const UNAUTHORIZED = 401;

const request = async (method, url, data) => {
  try {
    const result = await axios({
      method,
      url,
      data,
    });
    return result.data;
  } catch (result_1) {
    const { status } = result_1.response;
    if (status === UNAUTHORIZED) {
      return onUnauthorized();
    }
    throw Error(result_1);
  }
};

const onUnauthorized = () => {
  router.push('/login');
};

const auth = {
  login(username, password) {
    return request('post', '/api/auth/login', { username, password });
  },
};

const setAuthInHeader = (token) => {
  axios.defaults.headers.common['Authorization'] = token ? `Bearer ${token}` : null;
};

export { auth, setAuthInHeader };
