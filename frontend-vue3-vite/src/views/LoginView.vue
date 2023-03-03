<template>
  <h1>로그인 화면</h1>
  <div class="login">
    <input type="text" placeholder="email" v-model="user" />
    <input type="password" v-model="password" />
    <button type="submit" @click="login">로그인</button>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { useAuthStore } from '../stores/auth';
import { auth } from '../api';

const user = ref('');
const password = ref('');

const router = useRouter();
const store = useAuthStore();

const login = () => {
  auth
    .login(user.value, password.value)
    .then((response) => {
      console.log('accessToken: ' + response.accessToken);
      store.accessToken = response.accessToken;
      store.refreshToken = response.refreshToken;
      store.role = response.role;
      goHome();
    })
    .catch((e) => {
      console.error('error : ', e);
    });
};

const goHome = () => {
  if (store.isAdmin) {
    router.push('/admin');
  } else {
    router.push('/');
  }
};
</script>

<style scoped>
.outer {
  width: 400px;
  margin: auto;
}
</style>
