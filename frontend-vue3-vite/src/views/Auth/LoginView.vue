<template>
  <div class="bg-blue-500 justify-center text-center text-3xl font-semibold py-5 text-white">로그인</div>
  <div class="text-center bg-green-300">
    <div>
      <input id="email" type="text" placeholder=" 이메일" v-model="user" />
    </div>
    <div>
      <input id="password" type="password" placeholder=" 비밀번호" v-model="password" />
    </div>
    <button type="submit" @click="login">로그인</button>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { auth } from '../../api';

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
input {
  @apply border-solid border-2 border-blue-300 py-3 my-2 w-1/2;
}
button {
  @apply bg-blue-600 text-white hover:bg-blue-700 rounded py-3 my-2 w-1/2;
}
</style>
