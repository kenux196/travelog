import axios from 'axios';
import router from '../router/index';
import { useAuthStore } from '../stores/auth';

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

const setAuthInHeader = () => {
  const token = useAuthStore().accessToken;
  console.log(token);
  axios.defaults.headers.common['Authorization'] = token ? `Bearer ${token}` : null;
};

const onUnauthorized = () => {
  router.push('/login');
};

const auth = {
  async login(username, password) {
    try {
      const response = await request('post', '/api/auth/login', { username, password });
      console.log('accessToken: ' + response.accessToken);
      setToken(response);
      goHome();
    } catch (e) {
      console.error('logout error : ', e);
    }
  },
  async logout() {
    try {
      setAuthInHeader();
      await request('post', '/api/auth/logout');
      setToken(null);
      router.push('/login');
    } catch (e) {
      console.error('logout error : ', e);
    }
  },
  async refreshToken() {
    try {
      const token = useAuthStore().refreshToken;
      const data = await request('post', '/api/auth/refreshToken', { token });
      useAuthStore().accessToken = data.accessToken;
      console.log(`refresh token 성공: ${data.accessToken}`);
    } catch (e) {
      console.error('logout error : ', e);
    }
  },
};

const setToken = (data) => {
  const authSotre = useAuthStore();
  if (data === null) {
    authSotre.accessToken = '';
    authSotre.refreshToken = '';
    authSotre.role = 'anonymouse';
  } else {
    authSotre.accessToken = data.accessToken;
    authSotre.refreshToken = data.refreshToken;
    authSotre.role = data.role;
  }
};

const goHome = () => {
  if (useAuthStore().isAdmin) {
    router.push('/admin');
  } else {
    router.push('/');
  }
};

const admin = {
  getMembers() {
    setAuthInHeader();
    return request('get', '/api/admin/member');
  },
};

export { auth, admin };
