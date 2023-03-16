<template>
  <label>NAME: </label>
  <input type="text" v-model="result.name" placeholder="이름" />
  <label>AGE: </label>
  <input type="number" v-model="result.age" />
  <div class="grid">
    <label>GENDER: </label>
    <label for="gender-male"
      ><input id="gender-male" type="radio" name="gender" v-model="result.gender" value="male" />male</label
    >
    <label for="gender-female"
      ><input id="gender-female" type="radio" name="gender" v-model="result.gender" value="female" />female</label
    >
    <label>HOBBY: </label>
    <span v-for="hobby in hobby_options" :key="hobby">
      <label><input type="checkbox" v-model="result.hobby" :value="hobby" />{{ hobby }}</label>
    </span>
  </div>
  <label
    >PASSWORD:
    <input type="password" v-model="result.password" />
  </label>
  <label>COUNTRY</label>
  <select v-model="result.country">
    <option v-for="country in country_options" :key="country">{{ country }}</option>
  </select>
  <div>
    <h3>Result</h3>
    <hr />
    {{ result }}
  </div>
</template>

<script>
import { reactive } from 'vue';

export default {
  setup() {
    const name = '';
    const age = 0;
    const gender = 'male';
    const hobby = ['스포츠'];
    const password = '';
    const country = '한국';
    const country_options = ['한국', '미국', '중국'];
    const hobby_options = ['스포츠', '그림', '음악', '코딩'];

    const result = reactive({
      name,
      age,
      gender,
      hobby,
      password,
      country,
    });
    return {
      result,
      country_options,
      hobby_options,
    };
  },
};
</script>

<style lang="scss" scoped></style>
