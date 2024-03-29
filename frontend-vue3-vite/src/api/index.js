import axios from 'axios';
import router from '../router/index';
import { useAuthStore } from '../stores/auth';
import { useUserStore } from '../stores/user';
import { useRouterLinkStore } from '../stores/router-link-store';

const UNAUTHORIZED = 401;

const request = async (method, url, data, params) => {
  try {
    setAuthInHeader();
    const result = await axios({
      method,
      url,
      data,
      params,
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
  async signup(name, email, password) {
    try {
      await request('post', '/api/signup', { name, email, password });
      console.log('회원가입 완료');
      alert('회원가입을 성공했습니다. 로그인 후 이용하세요');
      router.push('/login');
    } catch (e) {
      console.log('Failed signup => ' + e.message);
      alert('회원가입을 실패하였습니다.');
    }
  },
  async login(username, password) {
    try {
      const data = await request('post', '/api/auth/login', { username, password });
      console.log('accessToken: ' + data.accessToken);
      setToken(data);
      // await this.getMySimpleInfo();
      goHome();
    } catch (e) {
      console.error('logout error : ', e);
    }
  },
  async logout() {
    try {
      await request('post', '/api/auth/logout');
      setToken(null);
      goHome();
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
  async getMySimpleInfo() {
    try {
      const data = await request('get', '/api/members/me', {}, {});
      useUserStore().name = data.name;
      console.log('login user name: ' + data.name);
    } catch (e) {
      console.error('getMySimpleInfo error: ', e);
    }
  },
};

const setToken = (data) => {
  const authSotre = useAuthStore();
  const userStore = useUserStore();
  if (data === null) {
    userStore.id = 0;
    authSotre.accessToken = '';
    authSotre.refreshToken = '';
    authSotre.role = 'anonymouse';
  } else {
    userStore.id = data.userId;
    authSotre.accessToken = data.accessToken;
    authSotre.refreshToken = data.refreshToken;
    authSotre.role = data.role;
  }
};

const goHome = () => {
  let to = '/';
  const routerLinkStore = useRouterLinkStore();
  if (useAuthStore().isAdmin) {
    to = '/admin';
    router.push(to);
  } else {
    router.push(to);
  }
  routerLinkStore.currentLink = to;
};

const admin = {
  getMembers() {
    return request('get', '/api/admin/members');
  },
};

export { auth, admin, request };
