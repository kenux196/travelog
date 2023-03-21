import { defineStore } from 'pinia';

export const useUserStore = defineStore('user', {
  state: () => {
    return {
      name: '',
      email: '',
    };
  },
  getters: {
    getInitial: (state) => {
      state.name.charAt(0).toUpperCase();
    },
    getName: (state) => {
      state.name;
    },
    getEmail: (state) => {
      state.email;
    },
  },
  actions: {
    setUserInfo(name, email) {
      this.name = name;
      this.email = email;
    },
  },
  persist: {
    enabled: true,
    strategies: [{ Storage: localStorage }],
  },
});
