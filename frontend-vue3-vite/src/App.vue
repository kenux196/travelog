<template>
  <header class="bg-slate-800 h-20">
    <nav>
      <div>
        <div v-if="authStore.isAdmin" class="text-3xl font-bold">JAMIRO ADMIN</div>
        <div v-else class="text-3xl font-bold">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 24 24"
            stroke-width="1.5"
            stroke="currentColor"
            class="w-6 h-6"
          >
            <path
              stroke-linecap="round"
              stroke-linejoin="round"
              d="M12 6.042A8.967 8.967 0 006 3.75c-1.052 0-2.062.18-3 .512v14.25A8.987 8.987 0 016 18c2.305 0 4.408.867 6 2.292m0-14.25a8.966 8.966 0 016-2.292c1.052 0 2.062.18 3 .512v14.25A8.987 8.987 0 0018 18a8.967 8.967 0 00-6 2.292m0-14.25v14.25"
            />
          </svg>
          JAMIRO
        </div>
      </div>
      <div class="flex mt-5">
        <ul class="flex space-x-4">
          <li>
            <router-link to="/" class="menu">Home</router-link>
          </li>
          <li>
            <router-link to="/booklog" class="menu">Book Log</router-link>
          </li>
          <li>
            <router-link to="/books" class="menu">Book</router-link>
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
