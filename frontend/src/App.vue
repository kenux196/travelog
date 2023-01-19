<template>
  <nav>
    <router-link to="/">Home</router-link> | <router-link to="/login">Login</router-link> |
    <router-link to="/about">About</router-link>
  </nav>
  <nav>
    <router-link to="#">관리자</router-link> | <router-link to="/admin">메인</router-link> |
    <router-link to="/admin/member">관리자 - 회원관리</router-link>
  </nav>
  <router-view />
</template>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #42b983;
}
</style>
