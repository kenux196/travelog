<template>
  <SwitchGroup>
    <div class="flex items-center">
      <SwitchLabel class="mr-4">Enable notifications</SwitchLabel>
      <Switch
        v-model="enabled"
        :class="enabled ? 'bg-blue-600' : 'bg-gray-200'"
        class="relative inline-flex h-6 w-11 items-center rounded-full transition-colors focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2"
      >
        <span
          :class="enabled ? 'translate-x-6' : 'translate-x-1'"
          class="inline-block h-4 w-4 transform rounded-full bg-white transition-transform"
        />
      </Switch>
    </div>
  </SwitchGroup>
  <div class="font-semibold text-3xl ml-5">text</div>
</template>

<script setup>
import { ref } from 'vue';
import { SwitchGroup, SwitchLabel, Switch } from '@headlessui/vue';

const enabled = ref(false);
</script>
