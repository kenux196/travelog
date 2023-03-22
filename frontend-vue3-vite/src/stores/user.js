import { defineStore } from 'pinia';

export const useUserStore = defineStore('user', {
  state: () => {
    return {
      id: 0,
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
    getId: (state) => {
      state.id;
    },
  },
  actions: {
    setUserInfo(id, name, email) {
      this.id = id;
      this.name = name;
      this.email = email;
    },
  },
  persist: {
    enabled: true,
    strategies: [{ Storage: localStorage }],
  },
});
