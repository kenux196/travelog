<template>
  <h1>로그인 화면</h1>
  <div class="login">
    <input type="text" placeholder="email" v-model="user" />
    <input type="password" v-model="password" />
    <button type="submit" @click="requestLogin()">로그인</button>
    <p v-if="loginError" class="error">Bad login information.</p>
  </div>
  <div class="protected" v-if="loginSuccess">
    <h5>{{ store.role }} 로그인 성공!</h5>
  </div>
  <div class="unprotected" v-else-if="loginError">
    <h5>로그인 실패!</h5>
  </div>
  <div class="unprotected" v-else>
    <h5>로그인 하지 않았습니다. 로그인을 해 주세요.</h5>
  </div>
</template>

<script setup>
// import axios from 'axios';
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { useAuthStore } from '../stores/auth';
import { login } from '../common/auth';

const loginSuccess = ref(false);
const loginError = ref(false);
const user = ref('');
const password = ref('');
// const error = ref(false);

const router = useRouter();
const store = useAuthStore();

async function requestLogin() {
  const loginSuccess = await login(user.value, password.value);
  if (loginSuccess) {
    moveToHome();
  } else {
    loginError.value = true;
  }
  // axios
  //   .post('/api/auth/login', {
  //     username: user.value,
  //     password: password.value,
  //   })
  //   .then((response) => {
  //     if (response.status === 200) {
  //       console.log(response.data);
  //       console.log('accessToken: ' + response.data.accessToken);
  //       store.accessToken = response.data.accessToken;
  //       store.refreshToken = response.data.refreshToken;
  //       store.role = response.data.role;
  //       loginSuccess.value = true;
  //       loginError.value = false;
  //       error.value = false;
  //       moveToHome();
  //     } else {
  //       loginSuccess.value = false;
  //       loginError.value = true;
  //       error.value = true;
  //     }
  //   })
  //   .catch((e) => {
  //     console.error('error : ', e);
  //   });
}

function moveToHome() {
  if (store.isAdmin) {
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
