<template>
  <div class="outer">
    <h1>로그인 화면</h1>
    <div id="loginForm">
      <b-form>
        <p>
          <b-form-input id="user" type="text" placeholder="Enter ID" v-model="user" />
        </p>
        <p>
          <b-form-input id="password" type="password" placeholder="Enter password" v-model="password" />
        </p>
        <b-button variant="primary" type="submit" @click="login()">로그인</b-button>
        <p v-if="error" class="error">Bad login information.</p>
      </b-form>
    </div>
    <div class="protected" v-if="loginSuccess">
      <h5>{{ userRole }}로그인 성공!</h5>
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
import store from '@/store/store';

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
      axios
        .post('/api/auth/login', {
          username: this.user,
          password: this.password,
        })
        .then(response => {
          if (response.status === 200) {
            console.log(response.data);
            console.log('accessToken: ' + response.data.accessToken);
            store.dispatch('setToken', response.data.accessToken);
            store.dispatch('setRefreshToken', response.data.refreshToken);
            store.dispatch('setRole', response.data.role);
            this.loginSuccess = true;
            this.loginError = false;
            this.error = false;
            this.moveToHome();
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
    moveToHome() {
      if (store.getters.isAdmin) {
        this.$router.push('/admin');
      } else {
        this.$router.push('/');
      }
    },
  },
};
</script>
<style>
.outer {
  width: 400px;
  margin: auto;
}
</style>
