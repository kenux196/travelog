import { defineStore } from 'pinia';

export const useMenuStore = defineStore('userMenu', {
  state: () => {
    return {
      selectedMenu: 0,
    };
  },
  getters: {
    getSeletedMenu: (state) => {
      state.selectedMenu;
    },
  },
  actions: {
    setSelectedMenu(menuId) {
      this.selectedMenu = menuId;
    },
  },
  persist: {
    enabled: true,
    strategies: [{ Storage: localStorage }],
  },
});
