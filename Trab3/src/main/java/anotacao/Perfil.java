package anotacao;
 import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
 import java.lang.annotation.Retention;
import java.lang.annotation.Target;
 @Retention(RUNTIME)
@Target(METHOD)
 
//Anotacao Criada @Perfil(nome="admin")
public @interface Perfil {
    String nome();
}
