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
    isLoggedIn: (state) => (state.refreshToken != '' ? true : false),
    isAdmin: (state) => state.role.split(',').includes('ROLE_ADMIN'),
  },
  actions: {
    setAuthentication(data) {
      this.accessToken = data.accessToken;
      this.refreshToken = data.refreshToken;
      this.role = data.role;
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
