import { ref, computed } from 'vue';
import { defineStore } from 'pinia';

export const useAuthStore = defineStore('auth', () => {
  const accessToken = ref('');
  const refreshToken = ref('');
  const role = ref('anonymouse');

  const isLoggedIn = computed(() => (accessToken.value != '' ? true : false));
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
    role,
    isLoggedIn,
    isAdmin,
    setAuthentication,
    updateAccessToken,
  };
});
