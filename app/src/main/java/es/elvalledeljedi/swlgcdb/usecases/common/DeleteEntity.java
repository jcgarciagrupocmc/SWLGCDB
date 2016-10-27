package es.elvalledeljedi.swlgcdb.usecases.common;

import es.elvalledeljedi.swlgcdb.common.repository.Repository;
import es.elvalledeljedi.swlgcdb.domain.Interactor.AbstractInteractor;
import es.elvalledeljedi.swlgcdb.domain.entity.BaseEntity;
import es.elvalledeljedi.swlgcdb.executor.InteractorExecutor;
import es.elvalledeljedi.swlgcdb.executor.MainThreadExecutor;

/**
 * Created by jcgarcia on 23/05/2016.
 */
public class DeleteEntity<T extends BaseEntity> extends AbstractInteractor {
    final static int ERROR_CODE = -1;
    Repository<T> localRepository;
    Repository<T> remoteRepository;
    T item;
    DeleteUseCaseCallback listener;
    int resultado = 0;

    public DeleteEntity(InteractorExecutor interactorExecutor, MainThreadExecutor mainThreadExecutor,
                        Repository<T> localRepository, Repository<T> remoteRepository) {
        super(interactorExecutor, mainThreadExecutor);
        this.localRepository = localRepository;
        this.remoteRepository = remoteRepository;
    }

    public void execute(T item, DeleteUseCaseCallback listener) {
        this.item = item;
        this.listener = listener;
        getInteractorExecutor().run(this);
    }

    @Override public void run() {

        try {

            deleteFromRemoteRepository();
            deleteFromLocalRepository();
            sendResultado();
        } catch (Exception e) {
            executionError();
        }
    }

    private void deleteFromLocalRepository() {

        if (resultado != ERROR_CODE && isRepositoryAvailable(localRepository)) {
            resultado = localRepository.delete(item);
        }

    }

    private void deleteFromRemoteRepository() {

        if (isRepositoryAvailable(remoteRepository)) {
            resultado = remoteRepository.delete(item);
        }
    }

    private boolean isRepositoryAvailable(Repository repository) {
        return repository != null && repository.getDataSource() != null;
    }

    private void sendResultado() {

        if (isResultOk()) {
            executionSuccessful();
        } else {
            executionError();
        }
    }

    private boolean isResultOk() {
        return resultado != ERROR_CODE;
    }

    protected void executionSuccessful() {
        if (listener != null) {
            getMainThreadExecutor().execute(new Runnable() {
                @Override public void run() {
                    listener.onDeleteUseCaseResponse(resultado);
                }
            });
        }
    }

    protected void executionError() {
        if (listener != null) {
            getMainThreadExecutor().execute(new Runnable() {
                @Override public void run() {
                    listener.onDeleteUseCaseError();
                }
            });
        }
    }

    public interface DeleteUseCaseCallback {
        void onDeleteUseCaseResponse(int resultado);

        void onDeleteUseCaseError();
    }
}
