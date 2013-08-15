package tk.pimabank

import org.apache.commons.lang.builder.HashCodeBuilder

class UserUserRole implements Serializable {

	User user
	UserRole userRole

	boolean equals(other) {
		if (!(other instanceof UserUserRole)) {
			return false
		}

		other.user?.id == user?.id &&
			other.userRole?.id == userRole?.id
	}

	int hashCode() {
		def builder = new HashCodeBuilder()
		if (user) builder.append(user.id)
		if (userRole) builder.append(userRole.id)
		builder.toHashCode()
	}

	static UserUserRole get(long userId, long userRoleId) {
		find 'from UserUserRole where user.id=:userId and userRole.id=:userRoleId',
			[userId: userId, userRoleId: userRoleId]
	}

	static UserUserRole create(User user, UserRole userRole, boolean flush = false) {
		new UserUserRole(user: user, userRole: userRole).save(flush: flush, insert: true)
	}

	static boolean remove(User user, UserRole userRole, boolean flush = false) {
		UserUserRole instance = UserUserRole.findByUserAndUserRole(user, userRole)
		if (!instance) {
			return false
		}

		instance.delete(flush: flush)
		true
	}

	static void removeAll(User user) {
		executeUpdate 'DELETE FROM UserUserRole WHERE user=:user', [user: user]
	}

	static void removeAll(UserRole userRole) {
		executeUpdate 'DELETE FROM UserUserRole WHERE userRole=:userRole', [userRole: userRole]
	}

	static mapping = {
		id composite: ['userRole', 'user']
		version false
	}
}
