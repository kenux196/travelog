<template>
  <div class="flex justify-between">
    <div class="py-4">
      <span class="bg-blue-500">&nbsp;</span>&nbsp;
      <strong>{{ state }}</strong>
    </div>
    <div class="dropdown">
      <label tabindex="0" class="btn m-1 bg-slate-800 hover:bg-slate-700">리스트 필터</label>
      <ul tabindex="0" class="dropdown-content menu p-2 shadow bg-base-100 rounded-box w-52">
        <li v-for="key in Object.keys(filters)" :key="key">
          <a @click="filter = key">{{ filters[key].str }}</a>
        </li>
      </ul>
    </div>
  </div>
</template>

<script setup>
import { ref, watch, computed, inject } from 'vue';

const emit = defineEmits(['change-filter']);
const filters = inject('filters');
const filter = ref(0);
const state = computed(() => {
  return filters[filter.value].str;
});
watch(
  () => filter.value,
  (filter) => {
    emit('change-filter', filter);
  },
);
</script>
