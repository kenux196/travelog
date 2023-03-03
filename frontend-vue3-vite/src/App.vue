<script setup>
import { RouterLink, RouterView, useRouter } from 'vue-router';
import { auth } from './api';
import { useAuthStore } from './stores/auth';

const router = useRouter();
const authStore = useAuthStore();

const logout = () => {
  auth
    .logout(authStore.accessToken)
    .then(() => {
      authStore.accessToken = '';
      authStore.refreshToken = '';
      authStore.role = 'anonymouse';
      router.push('/login');
    })
    .catch((e) => {
      console.error('logout error : ', e);
    });
};

const refreshToken = () => {
  auth
    .refreshToken(authStore.refreshToken)
    .then((data) => {
      authStore.accessToken = data.accessToken;
      console.log(data.accessToken);
    })
    .catch((e) => {
      console.error('logout error : ', e);
    });
};
</script>

<template>
  <header class="container">
    <nav>
      <ul>
        <li><strong>KENUX JAMIRO</strong></li>
      </ul>
      <ul>
        <li>
          <router-link to="/">Home</router-link>
        </li>
        <li>
          <router-link to="/about">About</router-link>
        </li>
        <li v-if="authStore.isAdmin">
          <router-link to="/admin">관리자메인화면</router-link>
        </li>
        <li v-if="authStore.isAdmin">
          <router-link to="/admin/member">회원관리(관리자)</router-link>
        </li>
        <li>
          <a v-if="authStore.isLoggedIn" @click="logout">Logout</a> |
          <a v-if="authStore.isLoggedIn" @click="refreshToken">RefresToken</a>
          <router-link v-else to="/login">Login</router-link>
        </li>
        <li>
          <router-link to="/learn">learn vue.js</router-link>
        </li>
      </ul>
    </nav>
  </header>
  <main class="container">
    <RouterView />
  </main>
</template>
