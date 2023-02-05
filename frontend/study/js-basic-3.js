// 참조에 의한 객체 복사
console.log('=> 참조에 의한 객체 복사 (얕은 복사)');
let user = { name: 'John' };
let admin = user;
console.log(user);
console.log(admin);
admin.name = 'Pete';
console.log(user.name);
console.log(user);
console.log(admin);

// 객체 복사
console.log('=> 객체 복사 (깊은 복사)');
let user_1 = {
  name: 'John',
  age: 30,
};

let clone = {};

for (let key in user_1) {
  clone[key] = user_1[key];
}
console.log(user_1);
console.log(clone);
clone.name = 'Pete';
console.log(user_1);
console.log(clone);

// Using Object.assign
console.log('Using Object.assign');
let user2 = { name: 'John' };
let permissions1 = { canView: true };
let permissions2 = { canEdit: true };
console.log(user2);
Object.assign(user2, permissions1, permissions2);
console.log(user2);
