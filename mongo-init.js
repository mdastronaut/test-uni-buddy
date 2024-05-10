db.createUser({
    user: 'root',
    pwd: 'root',
    roles: [
        {
            role: 'readWrite',
            db: 'test',
        },
    ],
});
db.createCollection('users1110', { capped: false });
db.createCollection('test0001', { capped: false });
