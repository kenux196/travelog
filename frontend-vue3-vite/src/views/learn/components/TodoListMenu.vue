<template>
  <div class="flex justify-center">
    <div>
      <div class="relative" data-te-dropdown-ref>
        <button
          class="flex items-center whitespace-nowrap rounded bg-primary px-6 pt-2.5 pb-2 text-xs font-medium uppercase leading-normal text-white shadow-[0_4px_9px_-4px_#3b71ca] transition duration-150 ease-in-out hover:bg-primary-600 hover:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] focus:bg-primary-600 focus:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] focus:outline-none focus:ring-0 active:bg-primary-700 active:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] motion-reduce:transition-none"
          type="button"
          id="dropdownMenuButton1"
          data-te-dropdown-toggle-ref
          aria-expanded="false"
          data-te-ripple-init
          data-te-ripple-color="light"
        >
          Dropdown button
          <span class="ml-2 w-2">
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor" class="h-5 w-5">
              <path
                fill-rule="evenodd"
                d="M5.23 7.21a.75.75 0 011.06.02L10 11.168l3.71-3.938a.75.75 0 111.08 1.04l-4.25 4.5a.75.75 0 01-1.08 0l-4.25-4.5a.75.75 0 01.02-1.06z"
                clip-rule="evenodd"
              />
            </svg>
          </span>
        </button>
        <ul
          class="absolute z-[1000] float-left m-0 hidden min-w-max list-none overflow-hidden rounded-lg border-none bg-white bg-clip-padding text-left text-base shadow-lg dark:bg-neutral-700 [&[data-te-dropdown-show]]:block"
          aria-labelledby="dropdownMenuButton1"
          data-te-dropdown-menu-ref
        >
          <li>
            <a
              class="block w-full whitespace-nowrap bg-transparent py-2 px-4 text-sm font-normal text-neutral-700 hover:bg-neutral-100 active:text-neutral-800 active:no-underline disabled:pointer-events-none disabled:bg-transparent disabled:text-neutral-400 dark:text-neutral-200 dark:hover:bg-neutral-600"
              href="#"
              data-te-dropdown-item-ref
              >Action</a
            >
          </li>
          <li>
            <a
              class="block w-full whitespace-nowrap bg-transparent py-2 px-4 text-sm font-normal text-neutral-700 hover:bg-neutral-100 active:text-neutral-800 active:no-underline disabled:pointer-events-none disabled:bg-transparent disabled:text-neutral-400 dark:text-neutral-200 dark:hover:bg-neutral-600"
              href="#"
              data-te-dropdown-item-ref
              >Another action</a
            >
          </li>
          <li>
            <a
              class="block w-full whitespace-nowrap bg-transparent py-2 px-4 text-sm font-normal text-neutral-700 hover:bg-neutral-100 active:text-neutral-800 active:no-underline disabled:pointer-events-none disabled:bg-transparent disabled:text-neutral-400 dark:text-neutral-200 dark:hover:bg-neutral-600"
              href="#"
              data-te-dropdown-item-ref
              >Something else here</a
            >
          </li>
        </ul>
      </div>
    </div>
  </div>
  <div class="row">
    <div class="col">
      <span class="bg-blue-500">&nbsp;</span>&nbsp;
      <strong>{{ state }}</strong>
    </div>
    <div class="col">
      <div class="flot-end">
        <button id="dropdownDefaultButton" data-dropdown-toggle="dropdown" type="button">리스트 필터</button>
        <!-- Dropdown menu -->
        <div id="dropdown"></div>
        <ul>
          <li v-for="key in Object.keys(filters)" :key="key">
            <a @click="filter = key">{{ filters[key].str }}</a>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, watch, computed, inject } from 'vue';

export default {
  name: 'TodoListMenu',
  emits: ['change-filter'],
  setup(props, context) {
    const filters = inject('filters');
    const filter = ref(0);
    const state = computed(() => {
      return filters[filter.value].str;
    });
    watch(
      () => filter.value,
      (filter) => {
        context.emit('change-filter', filter);
      },
    );
    return {
      state,
      filter,
      filters,
    };
  },
};
</script>

<style lang="scss" scoped></style>
