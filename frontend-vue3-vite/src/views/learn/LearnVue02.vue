<template>
  <h3>Vue test page 02</h3>
  <div>{{ date }}</div>
  <div>{{ date2 }}</div>
  <div v-text="date"></div>
  <p>줄임말과 원래말을 입력하세요.</p>
  <input type="text" :value="abbr" />
  <input type="text" v-model="normal" />
  <hr />
  <abbr v-bind:title="normal">{{ abbr }}</abbr>
  <MyComponent title="편지요~" :data="[1, 2, 3]"></MyComponent>
</template>
<script>
import { ref } from 'vue';
import MyComponent from './components/MyComponent.vue';
export default {
  // Composition API
  setup() {
    const date = Date().toString();
    const abbr = ref('DOPT');
    const normal = ref('Dong Project Team');
    return {
      date,
      abbr,
      normal,
    };
  },
  // Options API
  data() {
    return {
      date2: Date().toString(),
    };
  },
  components: { MyComponent },
};
</script>
