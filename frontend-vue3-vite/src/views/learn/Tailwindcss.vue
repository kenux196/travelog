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
  <div class="container mx-auto bg-zinc-800">
    <div class="text-white bg-green-900 mx-2 px-2 text-3xl py-3">
      <p class="my-2 bg-red-400 rounded-full">content 1</p>
      <p>content 2</p>
      <p>content 3</p>
    </div>
  </div>
  <div class="font-semibold text-3xl ml-5">text</div>
  <!-- <div class="columns-5 mx-4"> -->
  <div class="grid grid-cols-3 gap-4 mx-4">
    <div>
      <img
        class="w-20 h-20 rounded-full float-left mr-2"
        src="https://images.unsplash.com/photo-1679210529713-79345e8af07e?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHw1fHx8ZW58MHx8fHw%3D&auto=format&fit=crop&w=500&q=60"
      />
      <p class="text-ellipsis">
        Clean Code(클린 코드)』은 오브젝트 멘토(Object Mentor)의 동료들과 힘을 모아 ‘개발하며’ 클린 코드를 만드는 최상의
        애자일 기법을 소개하고 있다. 소프트웨어 장인 정신의 가치를 심어 주며 프로그래밍 실력을 높여줄 것이다. 여러분이
        노력만 한다면. 어떤 노력이 필요하냐고? 코드를 읽어야 한다. 아주 많은 코드를. 그리고 코드를 읽으면서 그 코드의
        무엇이 옳은지, 그른지 생각도 해야 한다. 좀 더 중요하게는 전문가로서 자신이 지니는 가치
      </p>
    </div>
    <div>
      <img
        class="w-full rounded-lg"
        src="https://images.unsplash.com/photo-1679210529713-79345e8af07e?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHw1fHx8ZW58MHx8fHw%3D&auto=format&fit=crop&w=500&q=60"
      />
      <div>Description</div>
      <p>이 차는 멋있는 자동차</p>
    </div>
    <div>
      <img
        class="w-full rounded-t-full"
        src="https://images.unsplash.com/photo-1679210529713-79345e8af07e?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHw1fHx8ZW58MHx8fHw%3D&auto=format&fit=crop&w=500&q=60"
      />
      <div>Description</div>
      <p>이 차는 멋있는 자동차</p>
    </div>
    <div>
      <img
        class="w-full rounded-bl-full"
        src="https://images.unsplash.com/photo-1679210529713-79345e8af07e?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHw1fHx8ZW58MHx8fHw%3D&auto=format&fit=crop&w=500&q=60"
      />
      <div>Description</div>
      <p>이 차는 멋있는 자동차 아아야앙</p>
    </div>
    <div>
      <img
        class="w-full rounded-bl-full"
        src="https://images.unsplash.com/photo-1679210529713-79345e8af07e?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHw1fHx8ZW58MHx8fHw%3D&auto=format&fit=crop&w=500&q=60"
      />
      <div>Description</div>
      <p>이 차는 멋있는 자동차 아아야앙</p>
    </div>
    <div>
      <img
        class="w-full rounded-bl-full"
        src="https://images.unsplash.com/photo-1679210529713-79345e8af07e?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHw1fHx8ZW58MHx8fHw%3D&auto=format&fit=crop&w=500&q=60"
      />
      <div>Description</div>
      <p>이 차는 멋있는 자동차 아아야앙</p>
    </div>
    <div>
      <img
        class="w-full rounded-bl-full"
        src="https://images.unsplash.com/photo-1679210529713-79345e8af07e?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHw1fHx8ZW58MHx8fHw%3D&auto=format&fit=crop&w=500&q=60"
      />
      <div>Description</div>
      <p>이 차는 멋있는 자동차 아아야앙</p>
    </div>
    <div>
      <img
        class="w-full rounded-bl-full"
        src="https://images.unsplash.com/photo-1679210529713-79345e8af07e?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHw1fHx8ZW58MHx8fHw%3D&auto=format&fit=crop&w=500&q=60"
      />
      <div>Description</div>
      <p>이 차는 멋있는 자동차 아아야앙</p>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { SwitchGroup, SwitchLabel, Switch } from '@headlessui/vue';

const enabled = ref(false);
</script>
