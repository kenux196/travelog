// 메서드와 this
let user = {
  name: 'kenux',
  age: 30,
  sayGooMorning: function () {
    console.log('Good morning!! ' + this.name);
  },
};
// 메서드 만들기
user.sayHi = function () {
  console.log('안녕하세요!' + user.name);
};
user.sayHi();

function sayBye() {
  console.log('Good bye!!');
}
user.sayBye = sayBye;
user.sayBye();
user.sayGooMorning();
console.log(user);

// new 연산자와 생성자 함수
