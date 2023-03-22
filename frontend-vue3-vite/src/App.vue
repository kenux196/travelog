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
        <MyInfoBadge v-if="authStore.isLoggedIn" />
      </div>
    </nav>
  </header>
  <main>
    <RouterView />
  </main>
</template>

<script setup>
import { RouterLink, RouterView } from 'vue-router';
import MyInfoBadge from './components/MyInfoBadge.vue';
import { useAuthStore } from './stores/auth';

const authStore = useAuthStore();
</script>

<style scoped>
.menu {
  @apply hover:text-yellow-300 font-extralight;
}
nav {
  @apply flex justify-between mx-10 text-neutral-200 items-center h-full;
}
</style>
