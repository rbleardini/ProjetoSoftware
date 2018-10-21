package dao.controle;


import net.sf.cglib.proxy.Enhancer;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import excecao.VariosCandidatosException;
import dao.ContaDAO;
import servico.controle.InterceptadorDeServico;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;

public class FabricaDeDAOs
{
    private static ResourceBundle prop;
    static
    {	try
    {	prop = ResourceBundle.getBundle("dao");
    }
    catch(MissingResourceException e)
    {	System.out.println("Aquivo dao.properties não encontrado.");
        throw new RuntimeException(e);
    }
    }
    @SuppressWarnings("unchecked")
    public static <T> T getDAO(Class<T> tipo)
    {
        T dao = null;
        String nomeDaClasse = null;
        try
        {
            Reflections reflections = new Reflections("dao.impl", new SubTypesScanner(false));
            Set<Class<? extends Object>> allClasses = reflections.getSubTypesOf(Object.class);
            boolean triggerException = false;
            for (Object item : allClasses){
                if(ContaDAO.class.isAssignableFrom(item.getClass())){
                    if(triggerException){
                        throw new VariosCandidatosException();
                    }
                    else{
                        nomeDaClasse = item.getClass().getName();
                        triggerException = true;
                    }
                }
            }
            dao = (T) Class.forName(nomeDaClasse).newInstance();
            Class<?> classeDoDAO = Class.forName(nomeDaClasse);
            return (T) Enhancer.create (classeDoDAO, new InterceptadorDeDAOs());
        }
        catch (InstantiationException e)
        {	System.out.println("Não foi possivel criar um objeto do tipo " + nomeDaClasse);
            throw new RuntimeException(e);
        }
        catch (IllegalAccessException e)
        {	System.out.println("Não foi possível criar um objeto do tipo " + nomeDaClasse);
            throw new RuntimeException(e);
        }
        catch (ClassNotFoundException e)
        {	System.out.println("Classe " + nomeDaClasse + " não encontrada");
            throw new RuntimeException(e);
        }
        catch(MissingResourceException e)
        {	System.out.println("Chave " + tipo + " não encontrada em dao.properties");
            throw new RuntimeException(e);
        }
    }
}
