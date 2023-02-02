<template>
  <b-navbar type="light" variant="light">
    <b-navbar-nav>
      <b-nav-item to="/">Home</b-nav-item>
      <b-nav-item to="/login">Login</b-nav-item>
      <b-nav-item to="/about">About</b-nav-item>
      <b-nav-item to="/admin">관리자-메인</b-nav-item>
      <b-nav-item to="/admin/member">관리자-회원관리</b-nav-item>
      <b-nav-item @click="logout">Logout</b-nav-item>
      <b-nav-item to="/vue-test">Vue.js Test</b-nav-item>
    </b-navbar-nav>
  </b-navbar>
  <router-view />
</template>
<script>
export default {
  data() {
    return {};
  },
  methods: {
    logout() {
      alert('logout api 호출 연결');
    },
  },
};
</script>

<style></style>
