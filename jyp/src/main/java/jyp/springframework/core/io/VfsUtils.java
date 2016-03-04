package jyp.springframework.core.io;

import org.springframework.util.ReflectionUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;

/**
 * @author jinyoung.park89
 * @since 2016-03-04
 */
public abstract class VfsUtils {
    private static final String VFS3_PKG = "org.jboss.vfs.";
    private static final String VFS_NAME = "VFS";
    private static Method VFS_METHOD_GET_ROOT_URL = null;
    private static Method VFS_METHOD_GET_ROOT_URI = null;
    private static Method VIRTUAL_FILE_METHOD_EXISTS = null;
    private static Method VIRTUAL_FILE_METHOD_GET_INPUT_STREAM;
    private static Method VIRTUAL_FILE_METHOD_GET_SIZE;
    private static Method VIRTUAL_FILE_METHOD_GET_LAST_MODIFIED;
    private static Method VIRTUAL_FILE_METHOD_TO_URL;
    private static Method VIRTUAL_FILE_METHOD_TO_URI;
    private static Method VIRTUAL_FILE_METHOD_GET_NAME;
    private static Method VIRTUAL_FILE_METHOD_GET_PATH_NAME;
    private static Method VIRTUAL_FILE_METHOD_GET_CHILD;
    protected static Class<?> VIRTUAL_FILE_VISITOR_INTERFACE;
    protected static Method VIRTUAL_FILE_METHOD_VISIT;
    private static Field VISITOR_ATTRIBUTES_FIELD_RECURSE = null;
    private static Method GET_PHYSICAL_FILE = null;

    public VfsUtils() {
    }

    protected static Object invokeVfsMethod(Method method, Object target, Object... args) throws IOException {
        try {
            return method.invoke(target, args);
        } catch (InvocationTargetException var5) {
            Throwable targetEx = var5.getTargetException();
            if(targetEx instanceof IOException) {
                throw (IOException)targetEx;
            }

            ReflectionUtils.handleInvocationTargetException(var5);
        } catch (Exception var6) {
            ReflectionUtils.handleReflectionException(var6);
        }

        throw new IllegalStateException("Invalid code path reached");
    }

    static boolean exists(Object vfsResource) {
        try {
            return ((Boolean)invokeVfsMethod(VIRTUAL_FILE_METHOD_EXISTS, vfsResource, new Object[0])).booleanValue();
        } catch (IOException var2) {
            return false;
        }
    }

    static boolean isReadable(Object vfsResource) {
        try {
            return ((Long)invokeVfsMethod(VIRTUAL_FILE_METHOD_GET_SIZE, vfsResource, new Object[0])).longValue() > 0L;
        } catch (IOException var2) {
            return false;
        }
    }

    static long getSize(Object vfsResource) throws IOException {
        return ((Long)invokeVfsMethod(VIRTUAL_FILE_METHOD_GET_SIZE, vfsResource, new Object[0])).longValue();
    }

    static long getLastModified(Object vfsResource) throws IOException {
        return ((Long)invokeVfsMethod(VIRTUAL_FILE_METHOD_GET_LAST_MODIFIED, vfsResource, new Object[0])).longValue();
    }

    static InputStream getInputStream(Object vfsResource) throws IOException {
        return (InputStream)invokeVfsMethod(VIRTUAL_FILE_METHOD_GET_INPUT_STREAM, vfsResource, new Object[0]);
    }

    static URL getURL(Object vfsResource) throws IOException {
        return (URL)invokeVfsMethod(VIRTUAL_FILE_METHOD_TO_URL, vfsResource, new Object[0]);
    }

    static URI getURI(Object vfsResource) throws IOException {
        return (URI)invokeVfsMethod(VIRTUAL_FILE_METHOD_TO_URI, vfsResource, new Object[0]);
    }

    static String getName(Object vfsResource) {
        try {
            return (String)invokeVfsMethod(VIRTUAL_FILE_METHOD_GET_NAME, vfsResource, new Object[0]);
        } catch (IOException var2) {
            throw new IllegalStateException("Cannot get resource name", var2);
        }
    }

    static Object getRelative(URL url) throws IOException {
        return invokeVfsMethod(VFS_METHOD_GET_ROOT_URL, (Object)null, new Object[]{url});
    }

    static Object getChild(Object vfsResource, String path) throws IOException {
        return invokeVfsMethod(VIRTUAL_FILE_METHOD_GET_CHILD, vfsResource, new Object[]{path});
    }

    static File getFile(Object vfsResource) throws IOException {
        return (File)invokeVfsMethod(GET_PHYSICAL_FILE, vfsResource, new Object[0]);
    }

    static Object getRoot(URI url) throws IOException {
        return invokeVfsMethod(VFS_METHOD_GET_ROOT_URI, (Object)null, new Object[]{url});
    }

    protected static Object getRoot(URL url) throws IOException {
        return invokeVfsMethod(VFS_METHOD_GET_ROOT_URL, (Object)null, new Object[]{url});
    }

    protected static Object doGetVisitorAttribute() {
        return ReflectionUtils.getField(VISITOR_ATTRIBUTES_FIELD_RECURSE, (Object)null);
    }

    protected static String doGetPath(Object resource) {
        return (String)ReflectionUtils.invokeMethod(VIRTUAL_FILE_METHOD_GET_PATH_NAME, resource);
    }

    static {
        ClassLoader loader = VfsUtils.class.getClassLoader();

        try {
            Class ex = loader.loadClass("org.jboss.vfs.VFS");
            VFS_METHOD_GET_ROOT_URL = ReflectionUtils.findMethod(ex, "getChild", new Class[]{URL.class});
            VFS_METHOD_GET_ROOT_URI = ReflectionUtils.findMethod(ex, "getChild", new Class[]{URI.class});
            Class virtualFile = loader.loadClass("org.jboss.vfs.VirtualFile");
            VIRTUAL_FILE_METHOD_EXISTS = ReflectionUtils.findMethod(virtualFile, "exists");
            VIRTUAL_FILE_METHOD_GET_INPUT_STREAM = ReflectionUtils.findMethod(virtualFile, "openStream");
            VIRTUAL_FILE_METHOD_GET_SIZE = ReflectionUtils.findMethod(virtualFile, "getSize");
            VIRTUAL_FILE_METHOD_GET_LAST_MODIFIED = ReflectionUtils.findMethod(virtualFile, "getLastModified");
            VIRTUAL_FILE_METHOD_TO_URI = ReflectionUtils.findMethod(virtualFile, "toURI");
            VIRTUAL_FILE_METHOD_TO_URL = ReflectionUtils.findMethod(virtualFile, "toURL");
            VIRTUAL_FILE_METHOD_GET_NAME = ReflectionUtils.findMethod(virtualFile, "getName");
            VIRTUAL_FILE_METHOD_GET_PATH_NAME = ReflectionUtils.findMethod(virtualFile, "getPathName");
            GET_PHYSICAL_FILE = ReflectionUtils.findMethod(virtualFile, "getPhysicalFile");
            VIRTUAL_FILE_METHOD_GET_CHILD = ReflectionUtils.findMethod(virtualFile, "getChild", new Class[]{String.class});
            VIRTUAL_FILE_VISITOR_INTERFACE = loader.loadClass("org.jboss.vfs.VirtualFileVisitor");
            VIRTUAL_FILE_METHOD_VISIT = ReflectionUtils.findMethod(virtualFile, "visit", new Class[]{VIRTUAL_FILE_VISITOR_INTERFACE});
            Class visitorAttributesClass = loader.loadClass("org.jboss.vfs.VisitorAttributes");
            VISITOR_ATTRIBUTES_FIELD_RECURSE = ReflectionUtils.findField(visitorAttributesClass, "RECURSE");
        } catch (ClassNotFoundException var4) {
            throw new IllegalStateException("Could not detect JBoss VFS infrastructure", var4);
        }
    }
}
