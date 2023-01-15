import { createStore } from 'vuex';

export default createStore({
  // 관리하고자 하는 상태
  state: {
    accessToken: null,
    refrestToken: null,
    role: 'anonymouse',
  },
  getters: {
    isLogin(state) {
      return state.accessToken == null ? false : true;
    },
    isAdmin(state) {
      return state.role == 'admin' ? true : false;
    },
    getAccessToken(state) {
      return state.accessToken;
    },
    getRefreshToken(state) {
      return state.refrestToken;
    },
  },
  // state를 변화시키는 로직
  mutations: {
    setToken(state, _accessToken) {
      state.accessToken = _accessToken;
    },
    setRefreshToken(state, _refreshToken) {
      state.refrestToken = _refreshToken;
    },
    setRole(state, _role) {
      state.role = _role;
    },
  },
  // mutation을 호출하는 서비스 로직으로 dispatch를 사용해서 호출한다.
  actions: {
    setToken: ({ commit }, _token) => {
      commit('setToken', _token);
    },
    setRefreshToken: ({ commit }, _refreshToken) => {
      commit('setRefreshToken', _refreshToken);
    },
    setRole: ({ commit }, _role) => {
      commit('setRole', _role);
    },
  },
  modules: {},
});
