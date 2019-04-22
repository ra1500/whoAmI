import tensorflow as tf
import numpy as np

const = tf.constant(2.0, name="const")

b = tf.placeholder(tf.float32, [None, 1], name='b')
c = tf.Variable(1.0, name='c')

d = tf.add(b, c, name='d')
e = tf.add(c, const, name='e')
a = tf.multiply(d, e, name='a')

# setup the variable initialisation
init_op = tf.global_variables_initializer()

# start the session, initialize the variables.
with tf.Session() as sess:
    sess.run(init_op)
    # compute the output of the graph
    # a_out = sess.run(a)
    a_out = sess.run(a, feed_dict={b: np.arange(0, 10)[:, np.newaxis]})
    print("Variable a is {}".format(a_out))