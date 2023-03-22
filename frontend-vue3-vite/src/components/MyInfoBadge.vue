<template>
  <div class="ml-4 -mt-2">
    <!-- <img
            src="https://images.unsplash.com/photo-1679032227470-8fe23399deac?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHw5fHx8ZW58MHx8fHw%3D&auto=format&fit=crop&w=500&q=60"
            class="w-10 h-10 rounded-full"
            @click="showUserMenu"
          /> -->
    <div class="w-10 h-10 rounded-full bg-slate-600 text-center" @click="showUserMenu">
      <div class="font-bold text-2xl py-1">
        {{ userName }}
      </div>
    </div>
    <div v-show="showMenu" class="absolute right-0 py-2 mt-2 bg-slate-800 rounded-md shadow-xl w-30">
      <a class="block px-4 py-2 text-sm menu" @click="logout">Logout</a>
      <a class="block px-4 py-2 text-sm menu" @click="refreshToken">RefresToken</a>
    </div>
  </div>
</template>

<script setup>
import { computed, ref } from 'vue';
import { useUserStore } from '@/stores/user';
import { auth } from '@/api';

const showMenu = ref(false);

const showUserMenu = () => {
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

const userStore = useUserStore();
const userName = computed(() => {
  return userStore.name.charAt(0).toUpperCase();
});
</script>

<style scoped></style>
