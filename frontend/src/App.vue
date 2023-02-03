<template>
  <div class="container">
    <div class="nav-area">
      <b-navbar type="light" variant="light">
        <b-navbar-brand tag="h1" class="mb-0">My TEST</b-navbar-brand>
        <b-collapse id="nav-collapse" is-nav>
          <b-navbar-nav>
            <b-nav-item to="/">Home</b-nav-item>
            <b-nav-item to="/login">Login</b-nav-item>
            <b-nav-item to="/about">About</b-nav-item>
            <b-nav-item to="/vue-test">Vue.js Test</b-nav-item>
            <b-nav-item v-if="isLoggedIn()" @click="logout">Logout</b-nav-item>
          </b-navbar-nav>
          <b-navbar-nav v-if="isAdmin()" class="ml-auto">
            <b-nav-item to="/admin">관리자-메인</b-nav-item>
            <b-nav-item to="/admin/member">관리자-회원관리</b-nav-item>
          </b-navbar-nav>
        </b-collapse>
      </b-navbar>
    </div>
    <router-view />
  </div>
</template>
<script>
import store from '@/store/store';
export default {
  data() {
    return {};
  },
  methods: {
    logout() {
      alert('logout api 호출 연결');
    },
    isLoggedIn() {
      return store.getters.isLogin;
    },
    isAdmin() {
      return store.getters.isAdmin;
    },
  },
};
</script>

<style>
#app {
  width: 100vw;
  height: 100vh;
  text-align: center;
}
.container {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
