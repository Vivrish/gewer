package cz.cvut.fit.ejk.service

import cz.cvut.fit.ejk.domain.model.User


class UserService(): CrudService<Int, User>(User) {

}