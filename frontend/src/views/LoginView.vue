<template>
  <div>
    <h1>로그인 화면</h1>
    <div id="loginForm">
      <b-form @submit.prevent="login()">
        <p>
          <b-form-input id="user" type="text" placeholder="Enter ID" v-model="user" />
        </p>
        <p>
          <b-form-input id="password" type="password" placeholder="Enter password" v-model="password" />
        </p>
        <b-button variant="primary" type="submit">로그인</b-button>
        <p v-if="error" class="error">Bad login information.</p>
      </b-form>
    </div>
    <div class="protected" v-if="loginSuccess">
      <h5>로그인 성공!</h5>
    </div>
    <div class="unprotected" v-else-if="loginError">
      <h5>로그인 실패!</h5>
    </div>
    <div class="unprotected" v-else>
      <h5>로그인 하지 않았습니다. 로그인을 해 주세요.</h5>
    </div>
  </div>
</template>
<script>
import axios from 'axios';

export default {
  name: 'loginView',
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
        const result = await axios.post('http://localhost:8080/api/login', {
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
<style>
.outer {
  text-align: center;
  width: 400px;
}
</style>
