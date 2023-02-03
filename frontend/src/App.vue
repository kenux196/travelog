<template>
  <div class="nav-area">
    <b-navbar type="light" variant="light">
      <b-navbar-brand tag="h1" class="mb-0">My TEST</b-navbar-brand>
      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav>
          <b-nav-item to="/">Home</b-nav-item>
          <b-nav-item to="/login">Login</b-nav-item>
          <b-nav-item to="/about">About</b-nav-item>
        </b-navbar-nav>
        <b-navbar-nav class="ml-auto">
          <b-nav-item to="/admin">관리자-메인</b-nav-item>
          <b-nav-item to="/admin/member">관리자-회원관리</b-nav-item>
          <b-nav-item @click="logout">Logout</b-nav-item>
          <b-nav-item to="/vue-test">Vue.js Test</b-nav-item>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
  </div>
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

<style>
body {
  margin: 10%;
}
#app {
  width: 100vw;
  height: 100vh;
  text-align: center;
}
</style>
