import { ref, computed } from 'vue';
import { defineStore } from 'pinia';

export const useAuthStore = defineStore('auth', () => {
  const accessToken = ref(null);
  const refreshToken = ref(null);
  const role = ref('anonymouse');

  const isLoggedIn = computed(() => (accessToken.value != null ? true : false));
  const isAdmin = computed(() => role.value.split(',').includes('ROLE_ADMIN'));

  function setAuthentication(accessToken, refreshToken, role) {
    accessToken.value = accessToken;
    refreshToken.value = refreshToken;
    role.value = role;
  }

  function updateAccessToken(token) {
    accessToken.value = token;
  }

  return {
    accessToken,
    refreshToken,
    isLoggedIn,
    isAdmin,
    setAuthentication,
    updateAccessToken,
  };
});
