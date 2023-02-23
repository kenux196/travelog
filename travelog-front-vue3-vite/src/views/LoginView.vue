<template>
  <h1>로그인 화면</h1>
  <div class="login">
    <input type="text" placeholder="email" v-model="user" />
    <input type="password" v-model="password" />
    <button type="submit" @click="login()">로그인</button>
    <p v-if="error" class="error">Bad login information.</p>
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
</template>

<script setup>
import axios from 'axios';
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { useAuthStore } from '../stores/auth';

const loginSuccess = ref(false);
const loginError = ref(false);
const user = ref('');
const password = ref('');
const error = ref(false);

const router = useRouter();

async function login() {
  axios
    .post('/api/auth/login', {
      username: user.value,
      password: password.value,
    })
    .then((response) => {
      if (response.status === 200) {
        console.log(response.data);
        console.log('accessToken: ' + response.data.accessToken);
        useAuthStore.setAuthentication(response.data.accessToken, response.data.refreshToken, response.data.role);
        loginSuccess.value = true;
        loginError.value = false;
        error.value = false;
        moveToHome();
      } else {
        loginSuccess.value = false;
        loginError.value = true;
        error.value = true;
      }
    })
    .catch((e) => {
      console.error('error : ', e);
    });
}

function moveToHome() {
  if (useAuthStore.isAdmin) {
    router.push('/admin');
  } else {
    router.push('/');
  }
}
</script>

<style scoped>
.outer {
  width: 400px;
  margin: auto;
}
</style>
