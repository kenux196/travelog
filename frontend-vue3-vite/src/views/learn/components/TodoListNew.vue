<template>
  <section>
    <div>
      <input
        type="text"
        v-model="valObj.job"
        placeholder="여기에 할일을 적으세요"
        class="input input-bordered input-md w-full"
      />
      <div>입력한 값 : {{ valObj.job }}</div>
      <div class="flex justify-between my-2">
        <input type="date" v-model="valObj.date" :min="today" class="input input-bordered input-md w-full max-w-xs" />
        <button @click="onAddTodo" class="btn bg-slate-800 hover:bg-slate-700">할일 추가</button>
      </div>
    </div>
  </section>
</template>

<script>
import { inject, ref } from 'vue';

export default {
  name: 'TodoListNew',
  setup() {
    const today = inject('today');
    const addTodo = inject('addTodo');
    const valObj = ref({
      job: '',
      date: today,
      today: today,
    });

    const onAddTodo = () => {
      if (valObj.value.job.length > 0) {
        console.log('call onAddTodo = ' + valObj.value.job);
        addTodo(valObj.value.job, valObj.value.date);
        valObj.value.job = '';
        valObj.value.date = today;
      }
    };
    return {
      today,
      valObj,
      onAddTodo,
    };
  },
};
</script>
