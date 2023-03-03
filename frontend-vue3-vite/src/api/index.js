import axios from 'axios';
import router from '../router/index';

const UNAUTHORIZED = 401;

const request = async (method, url, data) => {
  try {
    const result = await axios({
      method,
      url,
      data,
    });
    return result.data;
  } catch (error) {
    const { status } = error.response;
    if (status === UNAUTHORIZED) {
      return onUnauthorized();
    }
    throw Error(error);
  }
};

const onUnauthorized = () => {
  router.push('/login');
};

const auth = {
  login(username, password) {
    return request('post', '/api/auth/login', { username, password });
  },
  logout(token) {
    setAuthInHeader(token);
    return request('post', '/api/auth/logout');
  },
  refreshToken(token) {
    return request('post', '/api/auth/refreshToken', { token });
  },
};

const setAuthInHeader = (token) => {
  axios.defaults.headers.common['Authorization'] = token ? `Bearer ${token}` : null;
};

export { auth, setAuthInHeader };
