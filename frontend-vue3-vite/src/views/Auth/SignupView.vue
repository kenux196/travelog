<template>
  <div class="text-center text-3xl font-semibold py-5">회원가입</div>
  <div class="text-center">
    <div>
      <input type="text" placeholder="이름" v-model="name" />
    </div>
    <div>
      <input type="email" placeholder="이메일" v-model="email" />
    </div>
    <div>
      <input type="password" placeholder="비밀번호" v-model="password" />
    </div>
    <button type="submit" @click="signup">가입하기</button>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { auth } from '../../api';

const name = ref('');
const email = ref('');
const password = ref('');

const signup = () => {
  try {
    validate();
    auth.signup(name.value, email.value, password.value);
  } catch (e) {
    alert(e.message);
  }
};

const validate = () => {
  if (name.value === '') throw Error('이름을 입력하세요');
  if (email.value === '') throw Error('이메일 입력하세요');
  if (password.value === '') throw Error('패스워드를 입력하세요');
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
