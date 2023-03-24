import { defineStore } from 'pinia';

export const useRouterLinkStore = defineStore('router-link', {
  state: () => {
    return {
      currentLink: '',
    };
  },
  getters: {
    getSeletedMenu: (state) => {
      state.currentLink;
    },
  },
  actions: {
    setSelectedMenu(link) {
      this.currentLink = link;
    },
  },
  persist: {
    enabled: true,
    strategies: [{ Storage: localStorage }],
  },
});
