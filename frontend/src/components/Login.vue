<template>
  <div class="protected" v-if="loginSuccess">
    <h5>로그인 성공!</h5>
  </div>
  <div class="unprotected" v-else-if="loginError">
    <h5>로그인 실패!</h5>
  </div>
  <div class="unprotected" v-else>
    <h5>로그인 하지 않았습니다. 로그인을 해 주세요.</h5>
  </div>
  <form @submit.prevent="login()">
    <label>
      <input type="text" placeholder="username" v-model="user" />
    </label>
    <label>
      <input type="password" placeholder="password" v-model="password" />
    </label>
    <button variant="success" type="submit">Login</button>
    <p v-if="error" class="error">Bad login information.</p>
  </form>
</template>

<script>
import axios from 'axios';

export default {
  name: 'LoginItem',
  data() {
    return {
      loginSuccess: false,
      loginError: false,
      user: '',
      password: '',
      error: false,
    };
  },
  methods: {
    async login() {
      try {
        const result = await axios.get('/api/login', {
          auth: {
            username: this.user,
            password: this.password,
          },
        });
        if (result.status === 200) {
          this.loginSuccess = true;
        }
      } catch (err) {
        this.loginError = true;
        throw new Error(err);
      }
    },
  },
};
</script>

<style scoped></style>
