CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL
);

CREATE TABLE tags (
                      id SERIAL PRIMARY KEY,
                      name VARCHAR(255) NOT NULL
);

CREATE TABLE posts (
                       id SERIAL PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       content TEXT NOT NULL,
                       user_id INTEGER REFERENCES users(id),
                       category_id INTEGER REFERENCES categories(id)
);

CREATE TABLE likes (
                       id SERIAL PRIMARY KEY,
                       user_id INTEGER REFERENCES users(id),
                       post_id INTEGER REFERENCES posts(id)
);

CREATE TABLE forums (
                        id SERIAL PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        description TEXT NOT NULL
);

CREATE TABLE comments (
                          id SERIAL PRIMARY KEY,
                          content TEXT NOT NULL,
                          user_id INTEGER REFERENCES users(id),
                          post_id INTEGER REFERENCES posts(id)
);

CREATE TABLE categories (
                            id SERIAL PRIMARY KEY,
                            name VARCHAR(255) NOT NULL,
                            description TEXT NOT NULL
);
