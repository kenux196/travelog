import { defineStore } from 'pinia';

export const useAuthStore = defineStore('auth', {
  state: () => {
    return {
      accessToken: '',
      refreshToken: '',
      role: 'anonymouse',
    };
  },
  getters: {
    isLoggedIn: (state) => (state.accessToken != '' ? true : false),
    isAdmin: (state) => state.role.split(',').includes('ROLE_ADMIN'),
  },
  actions: {
    setAuthentication(accessToken, refreshToken, role) {
      this.accessToken = accessToken;
      this.refreshToken = refreshToken;
      this.role = role;
    },
    updateAccessToken(token) {
      this.accessToken = token;
    },
  },
  persist: {
    enabled: true,
    strategies: [{ Storage: localStorage }],
  },
});
