<template>
  <header class="bg-slate-800 h-20">
    <nav>
      <div>
        <div v-if="authStore.isAdmin" class="text-3xl font-bold">JAMIRO ADMIN</div>
        <div v-else class="text-3xl font-bold">JAMIRO</div>
      </div>
      <div class="flex mt-5">
        <ul class="flex space-x-4">
          <li>
            <router-link to="/" class="menu">Home</router-link>
          </li>
          <li>
            <router-link to="/books" class="menu">Book</router-link>
          </li>
          <li>
            <router-link to="/about" class="menu">About</router-link>
          </li>
          <li>
            <router-link to="/learn" class="menu">learn vue.js</router-link>
          </li>
          <li>
            <router-link v-if="!authStore.isLoggedIn" to="/login" class="menu">Login</router-link>
          </li>
          <li>
            <router-link v-if="!authStore.isLoggedIn" to="/signup" class="menu">Signup</router-link>
          </li>
        </ul>
        <ul v-if="authStore.isAdmin" class="flex space-x-4">
          <li v-if="authStore.isAdmin">
            <router-link to="/admin" class="menu">Admin-Home</router-link>
          </li>
          <li v-if="authStore.isAdmin">
            <router-link to="/admin/member" class="menu">Members</router-link>
          </li>
        </ul>
        <div v-if="authStore.isLoggedIn" class="ml-4 -mt-2">
          <img
            src="https://images.unsplash.com/photo-1679032227470-8fe23399deac?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHw5fHx8ZW58MHx8fHw%3D&auto=format&fit=crop&w=500&q=60"
            class="w-10 h-10 rounded-full"
            @click="showUserMenu"
          />
          <div v-show="show" class="absolute right-0 py-2 mt-2 bg-slate-800 rounded-md shadow-xl w-30">
            <a class="block px-4 py-2 text-sm menu" @click="logout">Logout</a>
            <a class="block px-4 py-2 text-sm menu" @click="refreshToken">RefresToken</a>
          </div>
        </div>
      </div>
    </nav>
  </header>
  <main>
    <RouterView />
  </main>
</template>

<script setup>
import { ref } from 'vue';
import { RouterLink, RouterView } from 'vue-router';
import { auth } from './api';
import { useAuthStore } from './stores/auth';

const authStore = useAuthStore();
const show = ref(true);

const showUserMenu = () => {
  show.value = !show.value;
};

const logout = () => {
  show.value = false;
  auth.logout();
};

const refreshToken = () => {
  show.value = false;
  auth.refreshToken();
};
</script>

<style scoped>
.menu {
  @apply hover:text-yellow-300 font-extralight;
}
nav {
  @apply flex justify-between mx-10 text-neutral-200 items-center h-full;
}
</style>
