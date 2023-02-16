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
        <li>
          <router-link to="/vue-test">Vue.js Test</router-link>
        </li>
        <li v-if="isAdmin()">
          <router-link to="/admin">관리자-메인</router-link>
        </li>
        <li v-if="isAdmin()">
          <router-link to="/admin/member">관리자-회원관리</router-link>
        </li>
        <li>
          <a v-if="isLoggedIn()" @click="logout">Logout</a>
          <router-link v-else to="/login">Login</router-link>
        </li>
      </ul>
    </nav>
  </header>
  <main class="container">
    <router-view />
  </main>
</template>
<script>
import store from '@/store/store';
import axios from 'axios';
export default {
  data() {
    return {};
  },
  methods: {
    isLoggedIn() {
      return store.getters.isLogin;
    },
    isAdmin() {
      return store.getters.isAdmin;
    },
    async logout() {
      axios
        .post(
          '/api/auth/logout',
          {},
          {
            headers: {
              Authorization: 'Bearer ' + store.getters.getAccessToken,
            },
          },
        )
        .then(response => {
          if (response.status === 200) {
            store.dispatch('setToken', null);
            store.dispatch('setRefreshToken', null);
            store.dispatch('setRole', 'anonymouse');
            this.$router.push('/login');
          } else {
            alert(response.status);
          }
        })
        .catch(e => {
          console.error('error : ', e);
        });
    },
  },
};
</script>
<style></style>
