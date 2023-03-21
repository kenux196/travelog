<script setup>
import { RouterLink, RouterView } from 'vue-router';
import { auth } from './api';
import { useAuthStore } from './stores/auth';

const authStore = useAuthStore();
</script>

<template>
  <header class="bg-slate-800">
    <nav>
      <ul>
        <div class="text-3xl font-bold py-3">KENUX JAMIRO</div>
      </ul>
      <ul class="flex space-x-4 mt-6">
        <li>
          <router-link to="/">Home</router-link>
        </li>
        <li>
          <router-link to="/books">Book</router-link>
        </li>
        <li>
          <router-link to="/about">About</router-link>
        </li>
        <li>
          <a v-if="authStore.isLoggedIn" @click="auth.logout">Logout</a>
          <router-link v-else to="/login">Login</router-link>
        </li>
        <li>
          <a v-if="authStore.isLoggedIn" @click="auth.refreshToken">RefresToken</a>
          <router-link v-else to="/signup">Signup</router-link>
        </li>
        <li>
          <router-link to="/learn">learn vue.js</router-link>
        </li>
      </ul>
    </nav>
    <nav v-if="authStore.isAdmin">
      <ul>
        <div><strong>KENUX ADMIN</strong></div>
      </ul>
      <ul class="flex space-x-4">
        <li v-if="authStore.isAdmin">
          <router-link to="/admin">관리자메인화면</router-link>
        </li>
        <li v-if="authStore.isAdmin">
          <router-link to="/admin/member">회원관리(관리자)</router-link>
        </li>
      </ul>
    </nav>
  </header>
  <main>
    <RouterView />
  </main>
</template>
<style scoped>
li {
  @apply hover:text-yellow-300;
}
nav {
  @apply flex justify-between mx-10 text-gray-300;
}
</style>
