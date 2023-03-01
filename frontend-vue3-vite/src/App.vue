<script setup>
import axios from 'axios';
import { RouterLink, RouterView, useRouter } from 'vue-router';
import { useAuthStore } from './stores/auth';

const router = useRouter();
const authStore = useAuthStore();

async function logout() {
  axios
    .post(
      '/api/auth/logout',
      {},
      {
        headers: {
          Authorization: 'Bearer ' + authStore.accessToken,
        },
      },
    )
    .then((response) => {
      if (response.status === 200) {
        authStore.accessToken = '';
        authStore.refreshToken = '';
        authStore.role = 'anonymouse';
        router.push('/login');
      } else {
        alert(response.status);
      }
    })
    .catch((e) => {
      console.error('error : ', e);
    });
}
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
          <a v-if="authStore.isLoggedIn" @click="logout">Logout</a>
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
