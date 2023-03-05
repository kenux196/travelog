import axios from 'axios';
import router from '../router/index';
import { useAuthStore } from '../stores/auth';

const UNAUTHORIZED = 401;

const request = async (method, url, data) => {
  try {
    setAuthInHeader();
    const result = await axios({
      method,
      url,
      data,
    });
    return result.data;
  } catch (error) {
    const { status, code } = error.response.data;
    console.log(error.response.data.code);
    if (status === UNAUTHORIZED) {
      if (code === 'A101' && url !== '/api/auth/logout') {
        console.error('토큰 만료: need token refresh');
        return onTokenExpired();
      }
      console.error('미인증 사용자의 요청');
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
  setToken(null);
  router.push('/login');
};

const onTokenExpired = () => {
  useAuthStore().accessToken = '';
  auth
    .refreshToken()
    .then(() => {
      console.log('refreshed token');
      router.go(0);
    })
    .catch(() => {
      router.push('/login');
    });
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
      console.error('refresh token error : ', e);
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
    return request('get', '/api/admin/members');
  },
};

export { auth, admin };
