<template>
  <div class="ml-4 -mt-2">
    <div class="w-10 h-10 rounded-full bg-slate-600 text-center" @click="showUserMenu">
      <div ref="dropdownMenu" class="font-bold text-2xl py-1">
        {{ userInitial }}
      </div>
    </div>
    <div v-show="showMenu" @click="test" class="absolute right-0 py-2 mt-2 bg-slate-800 rounded-md shadow-xl w-30">
      <a class="block px-4 py-2 text-sm menu" @click="logout">Logout</a>
      <a class="block px-4 py-2 text-sm menu" @click="refreshToken">RefresToken</a>
    </div>
  </div>
</template>

<script setup>
import { computed, onUnmounted, ref } from 'vue';
import { auth, request } from '@/api';

const showMenu = ref(false);
const userName = ref('');

const getMySimpleInfo = () => {
  request('get', '/api/members/me', {}, {}).then((data) => {
    console.log('login user name: ' + data.name);
    userName.value = data.name;
  });
};
getMySimpleInfo();

const showUserMenu = () => {
  console.log('showUserMenu');
  showMenu.value = !showMenu.value;
};

const logout = () => {
  showMenu.value = false;
  auth.logout();
};

const refreshToken = () => {
  showMenu.value = false;
  auth.refreshToken();
};

const userInitial = computed(() => {
  return userName.value.charAt(0).toUpperCase();
});

const dropdownMenu = ref(null);
const dropDownButtonClick = (e) => {
  if (dropdownMenu.value !== e.target) {
    showMenu.value = false;
  }
};
window.addEventListener('click', dropDownButtonClick);

onUnmounted(() => {
  window.removeEventListener('click', dropDownButtonClick);
});
</script>

<style scoped></style>
