<template>
  <h3 v-text="title"></h3>
  <span v-for="i in data" :key="i">{{ i }}</span>
</template>

<script>
import { ref } from 'vue';

export default {
  props: {
    title: {
      type: String,
      required: true,
      validator: function (value) {
        return typeof value == String;
      },
    },
    data: {
      type: Object,
      default: () => {
        return Array;
      },
    },
  },
  setup() {
    const count = ref(0);
    return { count };
  },
};
</script>

<style lang="scss" scoped></style>
