<template>
  <div class="justify-center text-center text-3xl font-semibold py-5">로그인</div>
  <div class="text-center">
    <div class="my-2">
      <input id="email" type="text" placeholder=" 이메일" v-model="user" />
    </div>
    <div class="my-2">
      <input id="password" type="password" placeholder=" 비밀번호" v-model="password" />
    </div>
    <div class="my-2">
      <button type="submit" @click="login">로그인</button>
    </div>
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
  @apply input input-bordered input-md w-1/2;
}
button {
  @apply btn w-1/2;
}
</style>
