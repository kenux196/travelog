<template>
  <div>
    <h1>로그인 화면</h1>
    <div id="loginForm">
      <b-form>
        <p>
          <b-form-input id="user" type="text" placeholder="Enter ID" v-model="user" />
        </p>
        <p>
          <b-form-input id="password" type="password" placeholder="Enter password" v-model="password" />
        </p>
        <b-button variant="primary" type="submit" @click="loginPost()">로그인:Post</b-button>
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
    async loginPost() {
      axios
        .post('/api/login', {
          username: this.user,
          password: this.password,
        })
        .then(response => {
          console.log(response.data);
          if (response.status === 200) {
            this.loginSuccess = true;
            this.loginError = false;
            this.error = false;
          } else {
            this.loginSuccess = false;
            this.loginError = true;
            this.error = true;
          }
        })
        .catch(e => {
          console.error('error : ', e);
        });
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
