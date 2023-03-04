<template>
  <h1>로그인 화면</h1>
  <div class="login">
    <input type="text" placeholder="이메일 계정을 입력하세요" v-model="user" />
    <input type="password" placeholder="암호를 입력하세요" v-model="password" />
    <button type="submit" @click="login">로그인</button>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { auth } from '../api';

const user = ref('');
const password = ref('');

const login = () => {
  if (user.value !== '' && password.value !== '') {
    auth.login(user.value, password.value);
    return;
  }
  alert('사용자 계정 및 암호를 입력하세요');
};
</script>

<style scoped>
.outer {
  width: 400px;
  margin: auto;
}
</style>
